package gay.pyrrha.dailyxp.core.result

import app.cash.turbine.test
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class ResultKtTest {
    @Test
    fun result_should_catch_errors() = runTest {
        val errorMessage = "Test done"
        flow {
            emit(1)
            throw Exception(errorMessage)
        }
            .asResult()
            .test {
                assertEquals(Result.Loading, awaitItem())
                assertEquals(Result.Success(1), awaitItem())

                when (val errorResult = awaitItem()) {
                    is Result.Error -> assertEquals(
                        errorMessage,
                        errorResult.exception.message
                    )
                    Result.Loading,
                    is Result.Success
                    -> throw IllegalStateException(
                        "The flow should have emitted an Error Result"
                    )
                }

                awaitComplete()
            }
    }
}
