import java.time.LocalDate

data class DataColumns (
    val speaker: String,
    val topic: String,
    val date: LocalDate,
    val words: Int
)