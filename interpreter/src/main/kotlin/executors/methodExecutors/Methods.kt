package interpreter.executors.methodExecutors

import astn.OpTree
import interpreter.Value

interface Methods {
    fun canExecute(methodName: String): Boolean

    fun execute(
        value: OpTree,
        variables: MutableMap<String, Value>,
    ): String
}
