package com.rsschool.quiz.data

object AnswerStorage {

    val answers = MutableList(QuestionStorage.getQuestionCount()) { -1 }

    fun getAnswer(index: Int): Int {
        return answers[index]
    }

    fun setAnswer(questionPosition: Int, answerPosition: Int) {
        answers[questionPosition] = answerPosition
    }

    fun reset() {
        answers.fill(-1)
    }
}