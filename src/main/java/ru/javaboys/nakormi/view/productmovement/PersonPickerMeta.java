package ru.javaboys.nakormi.view.productmovement;

import ru.javaboys.nakormi.entity.Person;
import ru.javaboys.nakormi.entity.PersonTypes;

public class PersonPickerMeta extends FieldMeta<Person> {
    private final PersonTypes type;

    public PersonPickerMeta(boolean enable, Person value, PersonTypes type) {
        super(enable, value);
        this.type = type;
    }

    public PersonTypes getType() {
        return type;
    }

    public static PersonPickerMeta enabled() {
        return new PersonPickerMeta(false, null, null);
    }

    public static PersonPickerMeta disabled() {
        return new PersonPickerMeta(false, null, null);
    }

    public static PersonPickerMeta enabled(PersonTypes type) {
        return new PersonPickerMeta(true, null, type);
    }

    public static PersonPickerMeta disabled(Person value) {
        return new PersonPickerMeta(false, value, null);
    }
}