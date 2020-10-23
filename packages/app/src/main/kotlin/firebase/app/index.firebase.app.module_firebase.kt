@file:JsModule("firebase/app")
@file:JsNonModule
@file:JsQualifier("app")
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS",
    "EXTERNAL_DELEGATION"
)

package firebase.app

import firebase.installations.Installations
import kotlin.js.Promise

external interface App {
    fun delete(): Promise<Any>
    fun installations(): Installations
    var name: String
    var options: Any
}
