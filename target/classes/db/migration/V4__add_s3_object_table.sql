CREATE TABLE IF NOT EXISTS `s3_bucket_objects` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bucket_id` INT(11) NOT NULL,
  `bucket_name` VARCHAR(1000) NOT NULL,
  `file_name` VARCHAR(1000) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`bucket_id`) REFERENCES s3_details (`id`),
  FOREIGN KEY (`bucket_name`) REFERENCES s3_details (`bucket_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;