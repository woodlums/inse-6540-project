create table reading_type
(
    type_code char(2)     not null
        primary key,
    type_name varchar(45) null
);

create table severity
(
    id          int         not null
        primary key,
    explanation varchar(45) null
);

create table logs
(
    id       int auto_increment
        primary key,
    date     datetime default current_timestamp() not null,
    log_text varchar(45)                          null,
    severity int                                  null,
    constraint fk_severity
        foreign key (severity) references severity (id)
);

create index fk_severity_idx
    on logs (severity);

create table units
(
    unit_code char        not null
        primary key,
    unit_name varchar(45) null
);

create table readings
(
    id                     bigint auto_increment
        primary key,
    timestamp              datetime default current_timestamp() not null,
    value                  float                                not null,
    unit                   char                                 not null,
    reading_type           char(2)                              not null,
    hashed_value           varchar(255)                         null,
    processed              bit      default b'0'                null,
    blockchain_transaction varchar(255)                         null,
    constraint fk_type_code
        foreign key (reading_type) references reading_type (type_code),
    constraint fk_unit
        foreign key (unit) references units (unit_code)
);

create index fk_reading_type_idx
    on readings (reading_type);

create index fk_unit_idx
    on readings (unit);

create definer = remote@`%` view readings_display as
select `inse6540`.`readings`.`timestamp`               AS `Timestamp`,
       `inse6540`.`readings`.`value`                   AS `Value`,
       `inse6540`.`units`.`unit_name`                  AS `Unit`,
       `inse6540`.`reading_type`.`type_name`           AS `Type`,
       `inse6540`.`readings`.`hashed_value`            AS `Unique Hash`,
       if(`inse6540`.`readings`.`processed`, '✔', '-') AS `Processed`,
       `inse6540`.`readings`.`blockchain_transaction`  AS `Blockchain Reference`
from ((`inse6540`.`readings` join `inse6540`.`units`) join `inse6540`.`reading_type`)
where `inse6540`.`units`.`unit_code` = `inse6540`.`readings`.`unit`
  and `inse6540`.`reading_type`.`type_code` = `inse6540`.`readings`.`reading_type`;

