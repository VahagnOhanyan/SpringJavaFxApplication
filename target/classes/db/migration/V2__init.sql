CREATE TABLE public.csv (
	num text NOT NULL,
	fullname text NOT NULL,
	monthyear text NOT NULL,
	worked float8 NULL,
	hospital float8 NULL,
	vacation float8 NULL,
	total float8 NOT NULL,
	CONSTRAINT onec_pkey PRIMARY KEY (num, monthyear)
);

CREATE TABLE public.csv_imported (
	monthyear text NOT NULL,
	CONSTRAINT monthyear PRIMARY KEY (monthyear)
);