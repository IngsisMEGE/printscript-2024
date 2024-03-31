package astBuilders

class BuilderProvider {
    private val list: List<AstBuilder> =
        listOf(
            AssignationBuilder(),
            MethodBuilder(),
            DeclarationAssignedBuilder(),
            DeclaratorBuilder(),
        )

    fun getBuilderList(): List<AstBuilder> = list
}
