CREATE TABLE public.book (
                             id varchar(10) NOT NULL,
                             judul varchar(100) NOT NULL,
                             pengarang varchar(100) NOT NULL,
                             penerbit varchar(100) NOT NULL,
                             tanggal_terbit date NOT NULL,
                             tebal_buku int4 NOT NULL,
                             CONSTRAINT buku_pkey PRIMARY KEY (id)
);

CREATE TABLE public.borrow (
                               id varchar(10) NOT NULL,
                               status varchar(2) NOT NULL,
                               peminjam varchar(100) NULL,
                               tanggal_pinjam date NULL,
                               tanggal_kembali date NULL,
                               CONSTRAINT borrow_pkey PRIMARY KEY (id),
                               CONSTRAINT borrow_fk FOREIGN KEY (id) REFERENCES book(id)
);