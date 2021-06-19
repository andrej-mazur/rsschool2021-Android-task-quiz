package com.rsschool.quiz.data

data class Question(
    val text: String,
    val options: List<QuestionOption>
)