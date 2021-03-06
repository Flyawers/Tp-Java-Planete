PGDMP     8                    v           planete    10.4    10.4                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                       false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                       false                       1262    24595    planete    DATABASE     �   CREATE DATABASE planete WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'French_France.1252' LC_CTYPE = 'French_France.1252';
    DROP DATABASE planete;
             postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
             postgres    false                       0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                  postgres    false    3                        3079    12924    plpgsql 	   EXTENSION     ?   CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;
    DROP EXTENSION plpgsql;
                  false            	           0    0    EXTENSION plpgsql    COMMENT     @   COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';
                       false    1            �            1259    24596    planetes_id_seq    SEQUENCE     x   CREATE SEQUENCE public.planetes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.planetes_id_seq;
       public       postgres    false    3            �            1259    24598    planetes    TABLE     /  CREATE TABLE public.planetes (
    plt_id integer DEFAULT nextval('public.planetes_id_seq'::regclass) NOT NULL,
    plt_nom character varying(100) NOT NULL,
    plt_temperature integer NOT NULL,
    plt_diametre integer NOT NULL,
    plt_id_type integer NOT NULL,
    plt_id_systeme integer NOT NULL
);
    DROP TABLE public.planetes;
       public         postgres    false    196    3            �            1259    24604    systeme_id_seq    SEQUENCE     w   CREATE SEQUENCE public.systeme_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.systeme_id_seq;
       public       postgres    false    3            �            1259    24606    systeme    TABLE     �   CREATE TABLE public.systeme (
    systeme_id integer DEFAULT nextval('public.systeme_id_seq'::regclass) NOT NULL,
    systeme_nom character varying(100)
);
    DROP TABLE public.systeme;
       public         postgres    false    198    3            �            1259    24612    type_id_seq    SEQUENCE     t   CREATE SEQUENCE public.type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.type_id_seq;
       public       postgres    false    3            �            1259    24614    type    TABLE     �   CREATE TABLE public.type (
    type_id integer DEFAULT nextval('public.type_id_seq'::regclass) NOT NULL,
    type_nom character varying(100)
);
    DROP TABLE public.type;
       public         postgres    false    200    3            �
          0    24598    planetes 
   TABLE DATA               o   COPY public.planetes (plt_id, plt_nom, plt_temperature, plt_diametre, plt_id_type, plt_id_systeme) FROM stdin;
    public       postgres    false    197   �       �
          0    24606    systeme 
   TABLE DATA               :   COPY public.systeme (systeme_id, systeme_nom) FROM stdin;
    public       postgres    false    199   �                 0    24614    type 
   TABLE DATA               1   COPY public.type (type_id, type_nom) FROM stdin;
    public       postgres    false    201   b       
           0    0    planetes_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.planetes_id_seq', 1, false);
            public       postgres    false    196                       0    0    systeme_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.systeme_id_seq', 1, false);
            public       postgres    false    198                       0    0    type_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.type_id_seq', 1, false);
            public       postgres    false    200            ~
           2606    24603    planetes planetes_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.planetes
    ADD CONSTRAINT planetes_pkey PRIMARY KEY (plt_id);
 @   ALTER TABLE ONLY public.planetes DROP CONSTRAINT planetes_pkey;
       public         postgres    false    197            �
           2606    24611    systeme systeme_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.systeme
    ADD CONSTRAINT systeme_pkey PRIMARY KEY (systeme_id);
 >   ALTER TABLE ONLY public.systeme DROP CONSTRAINT systeme_pkey;
       public         postgres    false    199            �
           2606    24619    type type_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY public.type
    ADD CONSTRAINT type_pkey PRIMARY KEY (type_id);
 8   ALTER TABLE ONLY public.type DROP CONSTRAINT type_pkey;
       public         postgres    false    201            �
   �   x��A
�0����S��$�Kҭ�q�ݹ�$RZHT�F�Ë����1�O��m���W�pQk�8}�w�˫�-3X�;A���kMϲ�Cd�9,�#�����cz,�����6�u�cW�m�KS�B����<�{�KR!XD��ѳ�,�eCD��)�      �
   �   x�����0���_���Uj�p�Q20х��4�����{�Y�b����8��d���A���2l��!&�GNwQ�i	�̕M������p��u}jZ��t+�Z�{��9�Q:�-8�c�q�iі�L���OF���t}�%��[❪����s*�k�,��6[j�)͢���<4�         $   x�3�I��)-�,,M�2�tO�J--N����� �i2     