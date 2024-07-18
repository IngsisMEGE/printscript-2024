

class PreSelectedEnvReader(private val envs: Map<String, String>) : EnvReader {
    override fun readEnv(envName: String): String? {
        return envs[envName]
    }
}
