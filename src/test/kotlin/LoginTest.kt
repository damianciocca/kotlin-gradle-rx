import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.test.assertTrue

class LanguageTest : Spek({

    given("a Language parser") {

        on("parsing 'spanish' in lowercase") {

            val language = Level()

            it("returns spanish language") {
                assertTrue{ language.addDefinition() }
            }
        }
    }
})