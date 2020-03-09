
DROP TABLE IF EXISTS `short_url`;
CREATE TABLE `short_url` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `short_key` varchar(8) COLLATE utf8_unicode_ci DEFAULT NULL,
  `count` bigint(20) NOT NULL DEFAULT '0',
  `hash` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `url` varchar(512) COLLATE utf8_unicode_ci NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_r8ede0oqkbe2lqx3hrovujl1i` (`short_key`),
  UNIQUE KEY `UK_4q3sixjkhor1wgw9sh1esqe4v` (`hash`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
