package $organization$

import org.apache.flink.api.common.typeinfo.{PrimitiveArrayTypeInfo, TypeInformation}
import org.apache.flink.streaming.util.serialization.{DeserializationSchema, SerializationSchema}

class FlinkByteArraySerializer extends DeserializationSchema[Array[Byte]] with SerializationSchema[Array[Byte]] {
  override def isEndOfStream(t: Array[Byte]): Boolean = false

  override def deserialize(bytes: Array[Byte]): Array[Byte] = bytes

  override def serialize(t: Array[Byte]): Array[Byte] = t

  override def getProducedType: TypeInformation[Array[Byte]] = PrimitiveArrayTypeInfo.BYTE_PRIMITIVE_ARRAY_TYPE_INFO
}
