package gay.pyrrha.dailyxp.core.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

/**
 * An [androidx.datastore.core.Serializer] for the [QuestsProto] proto.
 */
class QuestsSerializer @Inject constructor() : Serializer<QuestsProto> {
    override val defaultValue: QuestsProto = QuestsProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): QuestsProto =
        try {
            QuestsProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }

    override suspend fun writeTo(t: QuestsProto, output: OutputStream) {
        t.writeTo(output)
    }
}
