package com.rsschool.quiz.data

object DataStorage {

    val questions: List<Question> = listOf(
        Question(
            "Столица штата Гавайи?",
            listOf(
                QuestionOption("Берлингтон", false),
                QuestionOption("Гонолулу", true),
                QuestionOption("Таскалуса", false),
                QuestionOption("Льюистон", false),
                QuestionOption("Кахаба ", false)
            )
        ),
        Question(
            "Столица штата Джорджия?",
            listOf(
                QuestionOption("Уилинг", false),
                QuestionOption("Спрингфилд", false),
                QuestionOption("Атланта", true),
                QuestionOption("Сан-Хосе", false),
                QuestionOption("Монтерей", false)
            )
        ),
        Question(
            "Столица штата Колорадо?",
            listOf(
                QuestionOption("Денвер", true),
                QuestionOption("Колорадо-Спрингс", false),
                QuestionOption("Огаста", false),
                QuestionOption("Белмонт", false),
                QuestionOption("Джеймстаун", false)
            )
        ),
        Question(
            "Столица штата Массачусетс?",
            listOf(
                QuestionOption("Принстон", false),
                QuestionOption("Бостон", true),
                QuestionOption("Вашингтон", false),
                QuestionOption("Ланкастер", false),
                QuestionOption("Ноксвилл", false)
            )
        ),
        Question(
            "Столица штата Техас?",
            listOf(
                QuestionOption("Хьюстон", false),
                QuestionOption("Гатри", false),
                QuestionOption("Сан-Хуан", false),
                QuestionOption("Остин", true),
                QuestionOption("Омаха", false)
            )
        )
    )

    val selectedOptions = MutableList(DataStorage.getQuestionCount()) { -1 }

    fun getQuestion(index: Int): Question? {
        return questions.getOrNull(index)
    }

    fun getQuestionCount(): Int {
        return questions.size
    }

    fun isFirstQuestion(position: Int): Boolean {
        return position == 0
    }

    fun isFinalQuestion(position: Int): Boolean {
        return position == questions.size - 1
    }

    fun selectOption(questionPosition: Int, selectedOptionPosition: Int) {
        selectedOptions[questionPosition] = selectedOptionPosition
    }

    fun reset() {
        selectedOptions.fill(-1)
    }
}