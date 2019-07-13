package ru.skillbranch.devintensive.models

/**
 * Created by evgen.ru79@gmail.com on 12.07.2019.
 */
class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        try {
            question.validate(answer)
        } catch (error: Throwable) {
            return error.message!! to status.color
        }

        return if (question.answers.contains(answer.toLowerCase())) {
            when (question) {
                Question.IDLE -> "Отлично - ты справился\nНа этом все, вопросов больше нет" to status.color
                else -> {
                    question = question.nextQuestion()
                    "Отлично - ты справился\n${question.question}" to status.color
                }
            }
        } else {
            if (status == Status.CRITICAL) {
                status = Status.NORMAL
                question = Question.NAME
                "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
            } else {
                status = status.nextStatus()
                "Это неправильный ответ\n${question.question}" to status.color
            }
        }
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values().first()
            }
        }
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION
            override fun validate(answer: String) {
                if (answer.isEmpty() || answer.first().isLowerCase())
                    throw Exception("Имя должно начинаться с заглавной буквы\n${this.question}")
            }
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
            override fun validate(answer: String) {
                if (answer.isEmpty() || answer.first().isUpperCase())
                    throw Exception("Профессия должна начинаться со строчной буквы\n${this.question}")
            }
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
            override fun validate(answer: String) {
                if (answer.isEmpty() || answer.contains("[0-9]".toRegex()))
                    throw Exception("Материал не должен содержать цифр\n${this.question}")
            }
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
            override fun validate(answer: String) {
                if (answer.isEmpty() || answer.contains("[^0-9]".toRegex()))
                    throw Exception("Год моего рождения должен содержать только цифры\n${this.question}")
            }
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
            override fun validate(answer: String) {
                if (answer.isEmpty() || answer.contains("[^0-9]".toRegex()) || answer.length != 7)
                    throw Exception("Серийный номер содержит только цифры, и их 7\n${this.question}")
            }
        },
        IDLE("На этом все, вопросов больше нет", emptyList()) {
            override fun nextQuestion(): Question = IDLE
            override fun validate(answer: String) {
                throw Exception("На этом все, вопросов больше нет")
            }
        };

        abstract fun nextQuestion(): Question
        abstract fun validate(answer: String)
    }
}