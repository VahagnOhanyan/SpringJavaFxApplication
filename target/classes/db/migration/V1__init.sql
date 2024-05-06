CREATE TABLE public."user"
(
    user_id       serial4 primary key NOT NULL,
    user_surname  text                NOT NULL,
    user_name     text                NULL,
    user_midname  text                NULL,
    user_fullname text                NOT NULL,
    user_tel      text                NOT NULL,
    user_address  text                NOT NULL,
    user_email    text                NOT NULL,
    user_info_id  int4                NULL
);
INSERT INTO public."user"
(user_id, user_surname, user_name, user_midname, user_fullname, user_tel, user_address, user_email, user_info_id)
VALUES (1, 'super_user', 'super_user', 'super_user', 'super_user', 'нет', 'нет', 'нет', 1);

CREATE TABLE public.user_info
(
    user_info_id serial4 primary key NOT NULL,
    user_login   text                NOT NULL,
    user_pass    text                NOT NULL,
    user_role_id int4                NULL
);
INSERT INTO public.user_info
    (user_info_id, user_login, user_pass, user_role_id)
VALUES (1, 'super_user', 'super_user', 1);


CREATE TABLE public."privileges"
(
    privilege_id   serial4 primary key NOT NULL UNIQUE,
    privilege_name text                NOT NULL,
    access_type_id int4                NOT NULL,
    module_id      int4                NULL
);
CREATE TABLE public."role_privileges"
(
    role_privilege_id serial4 primary key NOT NULL UNIQUE,
    user_role_id      serial4             NOT NULL,
    privilege_id      serial4             NOT NULL

);

CREATE TABLE public."user_privileges"
(
    user_privilege_id serial4 primary key NOT NULL UNIQUE,
    user_info_id      serial4             NOT NULL,
    privilege_id      serial4             NOT NULL

);

CREATE TABLE public.access_section
(
    access_section_id   serial4 primary key NOT NULL,
    access_section_name text                NOT NULL

);
INSERT INTO public.access_section
    (access_section_id, access_section_name)
VALUES (1, 'Directories');
INSERT INTO public.access_section
    (access_section_id, access_section_name)
VALUES (2, 'Management');
INSERT INTO public.access_section
    (access_section_id, access_section_name)
VALUES (3, 'Tasks');
INSERT INTO public.access_section
    (access_section_id, access_section_name)
VALUES (4, 'Reports');
INSERT INTO public.access_section
    (access_section_id, access_section_name)
VALUES (5, 'Timesheet');

CREATE TABLE public."module"
(
    module_id         serial4 primary key NOT NULL,
    module_name       text                NOT NULL,
    access_section_id int4                NULL
);

INSERT INTO public."module"
    (module_id, module_name, access_section_id)
VALUES (1, 'Customer', 1);
INSERT INTO public."module"
    (module_id, module_name, access_section_id)
VALUES (2, 'Site', 1);
INSERT INTO public."module"
    (module_id, module_name, access_section_id)
VALUES (3, 'Employees', 1);
INSERT INTO public."module"
    (module_id, module_name, access_section_id)
VALUES (4, 'Contracts', 2);
INSERT INTO public."module"
    (module_id, module_name, access_section_id)
VALUES (5, 'Requests', 2);
INSERT INTO public."module"
    (module_id, module_name, access_section_id)
VALUES (6, 'Projects', 2);
INSERT INTO public."module"
    (module_id, module_name, access_section_id)
VALUES (7, 'Task', 3);
INSERT INTO public."module"
    (module_id, module_name, access_section_id)
VALUES (8, 'Report', 4);
INSERT INTO public."module"
    (module_id, module_name, access_section_id)
VALUES (9, 'Timesheet', 4);
INSERT INTO public."module"
    (module_id, module_name, access_section_id)
VALUES (10, 'Timesheet', 5);

CREATE TABLE public.access_type
(
    access_type_id   serial4 primary key NOT NULL,
    access_type_name text                NOT NULL

);
INSERT INTO public.access_type
    (access_type_id, access_type_name)
VALUES (1, 'Access');
INSERT INTO public.access_type
    (access_type_id, access_type_name)
VALUES (2, 'No Access');


CREATE TABLE public.calendar
(
    "year"      int4                NOT NULL,
    "month"     int4                NOT NULL,
    "day"       int4                NOT NULL,
    "type"      text                NOT NULL,
    calendar_id serial4 primary key NOT NULL
);

CREATE TABLE public.user_role
(
    user_role_id   serial4 primary key NOT NULL,
    user_role_name text                NOT NULL,
    sort           int4                NULL
);

INSERT INTO public.user_role
    (user_role_id, user_role_name, sort)
VALUES (1, 'Super_user', 1);