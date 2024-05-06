CREATE TABLE public.customer
(
    customer_id        serial4 NOT NULL,
    customer_name      text    NOT NULL,
    customer_full_name text NULL,
    CONSTRAINT customer_pk PRIMARY KEY (customer_id),
    CONSTRAINT customer_un UNIQUE (customer_name)
);
CREATE TABLE public.status
(
    status_id   serial4 NOT NULL,
    status_name text    NOT NULL,
    CONSTRAINT status_pk PRIMARY KEY (status_id)
);
CREATE TABLE public.project
(
    project_id   serial4 NOT NULL,
    project_name text    NOT NULL,
    customer_id  int4 NULL,
    status_id    int4 NULL,
    CONSTRAINT project_pk PRIMARY KEY (project_id),
    CONSTRAINT project_fk1 FOREIGN KEY (customer_id) REFERENCES public.customer (customer_id),
    CONSTRAINT project_status_fk FOREIGN KEY (status_id) REFERENCES public.status (status_id)
);

