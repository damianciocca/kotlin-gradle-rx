package vertx.clientservertest

import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.apache.commons.lang3.text.StrLookup
import org.apache.commons.lang3.text.StrSubstitutor
import java.util.*

/**
 * SINFLETON
 * As you can see, you just need to use the reserved word object instead of class and the rest is the same. Just take into account that objects can’t have a constructor, as we don’t call any constructors to access to them.
 * The instance of the object will be created the first time we use it. So there’s a lazy instantiation here: if an object is never used, the
 * instance will never be created.
 */
object EnvironmentConfig {

    /**
     * The lateinit indicates that this property won’t have value from the beginning, but will be assigned before it’s used (otherwise it’ll throw an exception).
     */
    lateinit var config: Map<*, *>

    fun set(environmentConfig: Map<*, *>) {
        config = environmentConfig
        println("CONFIG $config")
    }

    fun getString(configName: String): String {
        val s = Optional.ofNullable(config[configName])
                .map<String> { it.toString() }
                .orElse("")

        val result = EnvironmentVariableSubstitutor.replace(s)
        return result
    }

    fun getInt(configName: String): Int {
        val s = Optional.ofNullable(config[configName])
                .map<String> { it.toString() }
                .orElse("0")
        return Integer.valueOf(EnvironmentVariableSubstitutor.replace(s))
    }

    fun getBoolean(configName: String): Boolean {
        val s = Optional.ofNullable(config[configName])
                .map<String> { it.toString() }
                .orElse("false")

        return java.lang.Boolean.valueOf(EnvironmentVariableSubstitutor.replace(s))
    }

    // ESTO SE USA PARA DEVOLVER UN OBJ COMPLEGO.. EJ ConnectionConfig
	inline fun <reified T : Any> get(configName : String) : T {
		val map = Optional.ofNullable(config[configName]).orElse(mapOf<Any, Any>())
		return jacksonObjectMapper().convertValue(map)
	}
}

private object EnvironmentVariableSubstitutor : StrSubstitutor(EnvironmentVariableLookup()) {

    init {
        this.isEnableSubstitutionInVariables = false
    }

    private class EnvironmentVariableLookup : StrLookup<Any>() {

        override fun lookup(key: String): String? {
            return System.getenv(key)
        }
    }
}

