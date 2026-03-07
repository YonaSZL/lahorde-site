package yonasazela.lahordeapi.exceptions;

import org.junit.jupiter.api.Nested;

class ExceptionsTest extends ExceptionsBaseTest {

    @Nested
    class DB extends DBExceptionTest {}

    @Nested
    class NegativeId extends NegativeIdentifierExceptionTest {}

    @Nested
    class NullObject extends NullObjectExceptionTest {}

    @Nested
    class NullString extends NullStringParameterExceptionTest {}

    @Nested
    class ObjectAlreadyExists extends ObjectAlreadyExistsExceptionTest {}

    @Nested
    class ObjectNotFound extends ObjectNotFoundExceptionTest {}

    @Nested
    class ZeroUpdate extends ZeroUpdateIdentifierExceptionTest {}
}
