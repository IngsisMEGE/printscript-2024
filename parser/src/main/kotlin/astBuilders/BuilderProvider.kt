package astBuilders

/**
 * This class is responsible for providing a list of AstBuilder objects in the PrintScript application.
 * It contains a private list of AstBuilder objects and a function to get this list.
 *
 * The list is initialized with an AssignationBuilder object, a MethodBuilder object, a DeclarationAssignedBuilder object, and a DeclaratorBuilder object. These objects are used to build different types of ASTs in the application.
 *
 * The getBuilderList function returns the list of AstBuilder objects. This function can be used to get all the AstBuilder objects available in the application.
 */
class BuilderProvider {
    private val list: List<AstBuilder> =
        listOf(
            EmptyASTBuilder(),
            AssignationBuilder(),
            MethodBuilder(true),
            DeclarationAssignedBuilder(),
            DeclaratorBuilder(true),
            ElseBuilder(),
            IfBuilder(),
            CloseBuilder(),
        )

    fun getBuilderList(): List<AstBuilder> = list
}
