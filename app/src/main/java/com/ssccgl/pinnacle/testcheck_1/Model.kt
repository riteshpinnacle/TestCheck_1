package com.ssccgl.pinnacle.testcheck_1

data class Subject(
    val sb_id: Int,
    val ppr_id: Int,
    val subject_name: String
)

data class Detail(
    val qid: Int,
    val question_id: Int,
    val subject_id: Int,
    val question: String,
    val option1: String,
    val option2: String,
    val option3: String,
    val option4: String,
    val hindi_question: String,
    val positive_marks: String,
    val negative_marks: Double,
    val answered_ques: Int,
    val hrs: String,
    val mins: String,
    val secs: String,
    val answer: String
)

data class ApiResponse(
    val subjects: List<Subject>,
    val details: List<Detail>
)
