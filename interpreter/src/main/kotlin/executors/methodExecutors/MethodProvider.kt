package interpreter.executors.methodExecutors

import PrintLnMethod
import java.util.Optional

class MethodProvider {
    private val methods =
        listOf<Methods>(
            PrintLnMethod(),
        )

    fun getMethod(methodName: String): Optional<Methods> {
        return methods.stream().filter { it.canExecute(methodName) }.findFirst()
    }
}
