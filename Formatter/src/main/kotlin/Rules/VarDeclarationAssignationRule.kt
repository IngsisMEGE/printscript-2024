package Rules

import ASTN.AST
import ASTN.Operation
import Enforcers.AssignationSpaceEnforcer
import Enforcers.DoubleDotDeclarationEnforcer
import Enforcers.Enforcer

class VarDeclarationAssignationRule(
    private val DoubleDotSpaceInFrontName: String,
    private val DoubleDotSpaceInBackName: String,
    private val AssignationSpaceInFrontName: String,
    private val AssignationSpaceInBackName: String,
    override val enforcer: List<Enforcer> = listOf(),
    private val OperationRule: OperationRule = OperationRule()
) : Rules {
    override fun isTheRuleIncluded(property: Map<String, Any>): Rules {
        var enforcers: List<Enforcer> = enforcer
        OperationRule.isTheRuleIncluded(property)
        if (property.containsKey(DoubleDotSpaceInFrontName) && property.containsKey(DoubleDotSpaceInBackName)) {
            enforcers = enforcers.plus(
                DoubleDotDeclarationEnforcer(
                    property[DoubleDotSpaceInFrontName] as Int,
                    property[DoubleDotSpaceInBackName] as Int
                )
            )
        }
        if (property.containsKey(AssignationSpaceInFrontName) && property.containsKey(AssignationSpaceInBackName)) {
            enforcers = enforcers.plus(
                AssignationSpaceEnforcer(
                    property[AssignationSpaceInFrontName] as Int,
                    property[AssignationSpaceInBackName] as Int
                )
            )
        }
        return VarDeclarationAssignationRule(
            DoubleDotSpaceInFrontName,
            DoubleDotSpaceInBackName,
            AssignationSpaceInFrontName,
            AssignationSpaceInBackName,
            enforcers
        )
    }

    override fun enforceRule(code: String): String {
        var line = code
        for (enforcer in enforcer) {
            line = enforcer.enforceRule(line)
        }
        return line
    }

    override fun genericLine(ast: AST): String {
        when (ast) {
            is ASTN.VarDeclarationAssignation -> {
                val newLine = StringBuilder()
                newLine.append("let ")
                newLine.append(ast.varDeclaration.assignation.getValue())
                newLine.append(":")
                newLine.append(ast.varDeclaration.type.getValue())
                newLine.append("=")
                val operation = OperationRule.genericLine(Operation(ast.value))
                newLine.append(OperationRule.enforceRule(operation)) //Esto deberia cambiar si hacemos OPTree Rule
                newLine.append(";")


                //Hacer el var Declaration Rule del Operation. Y despues meterle la regla aca.
                return newLine.toString()
            }

            else -> {
                return ""
            }
        }
    }
}
