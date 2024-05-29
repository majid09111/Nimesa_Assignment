CREATE TABLE IF NOT EXISTS `s3_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_id` varchar(200) NOT NULL,
  `bucket_name` VARCHAR(1000) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_bucket_name` (`bucket_name`),
  FOREIGN KEY (`job_id`) REFERENCES job_details (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;