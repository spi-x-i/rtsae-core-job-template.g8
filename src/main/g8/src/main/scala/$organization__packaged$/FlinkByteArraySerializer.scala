package $organization$

import java.io.{ByteArrayOutputStream, Flushable}

import org.apache.avro.Schema
import org.apache.avro.io.{DecoderFactory, EncoderFactory}
import org.apache.avro.specific.{
  SpecificDatumReader,
  SpecificDatumWriter,
  SpecificRecordBase
}
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.streaming.util.serialization.{
  DeserializationSchema,
  SerializationSchema
}

import scala.util.control.NonFatal
import scala.util.{Failure, Success, Try}

trait SerDeSchema[T]
    extends DeserializationSchema[T]
    with SerializationSchema[T]

class AvroDeserializationSchema[T <: SpecificRecordBase: TypeInformation](
    schemaString: String)
    extends SerDeSchema[T] {

  lazy val schema = new Schema.Parser().parse(schemaString)
  lazy val reader = new SpecificDatumReader[T](schema)
  lazy val writer = new SpecificDatumWriter[T](schema)

  override def deserialize(message: Array[Byte]): T =
    reader.read(null.asInstanceOf[T],
                DecoderFactory.get.binaryDecoder(message, null))

  override def isEndOfStream(nextElement: T): Boolean = false

  override def getProducedType(): TypeInformation[T] =
    implicitly[TypeInformation[T]]

  override def serialize(element: T): Array[Byte] =
    closable(
      flushable(new ByteArrayOutputStream()) { baos =>
        flushable(EncoderFactory.get().binaryEncoder(baos, null)) { encoder =>
          writer.write(element, encoder)
        }
        baos
      }
    )(_.toByteArray).get

  private def flushable[A <: Flushable, B](flushable: A)(fun: A => B): B = {
    val result = fun(flushable)
    flushable.flush()
    result
  }

  private def closable[A <: AutoCloseable, B](f: A)(fn: A => B): Try[B] =
    try { Success(fn(f)) } catch { case NonFatal(e) => Failure(e) } finally {
      f.close()
    }

}
