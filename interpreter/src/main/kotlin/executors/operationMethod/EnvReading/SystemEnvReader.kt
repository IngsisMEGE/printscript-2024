

class SystemEnvReader : EnvReader {
    override fun readEnv(envName: String): String? {
        return System.getProperty(envName)
    }
}
