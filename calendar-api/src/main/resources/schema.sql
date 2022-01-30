create table person_slot (
    id VARCHAR(36) default random_uuid(),
    person_id VARCHAR(36) not null,
    start_date DATETIME not null,
    end_date DATETIME not null,
    primary key (id)
);
alter table person_slot add constraint c_person_id_start_date_end_date_unique unique(person_id, start_date, end_date);