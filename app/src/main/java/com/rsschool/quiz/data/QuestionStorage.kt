package com.rsschool.quiz.data

object QuestionStorage {

    val questions: List<Question> = listOf(
        Question(
            "Столица штата Гавайи?",
            listOf(
                AnswerOption("Берлингтон", false),
                AnswerOption("Гонолулу", true),
                AnswerOption("Таскалуса", false),
                AnswerOption("Льюистон", false),
                AnswerOption("Кахаба ", false)
            )
        ),
        Question(
            "Столица штата Джорджия?",
            listOf(
                AnswerOption("Уилинг", false),
                AnswerOption("Спрингфилд", false),
                AnswerOption("Атланта", true),
                AnswerOption("Сан-Хосе", false),
                AnswerOption("Монтерей", false)
            )
        ),
        Question(
            "Столица штата Колорадо?",
            listOf(
                AnswerOption("Денвер", true),
                AnswerOption("Колорадо-Спрингс", false),
                AnswerOption("Огаста", false),
                AnswerOption("Белмонт", false),
                AnswerOption("Джеймстаун", false)
            )
        ),
        Question(
            "Столица штата Массачусетс?",
            listOf(
                AnswerOption("Принстон", false),
                AnswerOption("Бостон", true),
                AnswerOption("Вашингтон", false),
                AnswerOption("Ланкастер", false),
                AnswerOption("Ноксвилл", false)
            )
        ),
        Question(
            "Столица штата Техас?",
            listOf(
                AnswerOption("Хьюстон", false),
                AnswerOption("Гатри", false),
                AnswerOption("Сан-Хуан", false),
                AnswerOption("Остин", true),
                AnswerOption("Омаха", false)
            )
        )
    )

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
}