package Parser.ASTBuilders

class BuilderProvider {
    private val list: List<AstBuilder> = listOf(
        AssignationBuilder(),
        MethodBuilder(),
        DeclarationAssignedBuilder(),
        DeclaratorBuilder(),
        OperationBuilder()
    )

    fun getBuilderList(): List<AstBuilder> = list
}