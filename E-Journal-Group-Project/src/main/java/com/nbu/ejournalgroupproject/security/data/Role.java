package com.nbu.ejournalgroupproject.security.data;

public enum Role {

    // Какво значи, че администраторът въвежда ролите и тн
    ADMINISTRATOR, // all endpoints?

    // Статистиките трябва ли да са на отделен ендпойнт и като цяло как могат да се имплементират?
    HEADMASTER, // само до неговото училище -> взимаме потребутеля, по неговото ид да се вземе училището endpoints for teachers, disciplines, students, caregivers?

    TEACHER, // endpoints for students, grades, absences - само че как се валидира дали може, защото му преподава през секюрити?

    CAREGIVER, // endpoints for their children only - как се постига това?

    STUDENT // only his things? това как се прави

}
