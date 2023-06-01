
import com.petpack.whereismyheart.data.model.user_details.ConnectionRequest
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String ?  = null,
    val subId : String? = null,
    val name: String?= null,
    val emailAddress: String?= null,
    val userHeartId: String?= null,
    val profilePhoto: String?= null,
    val connectedHeardId: String?= null,
    val connectedUserName: String? = null,
    val connectedUserEmail: String? = null,
    val connectUserPhoto: String? = null,
    val listOfConnectRequest : List<ConnectionRequest?> = listOf()
)