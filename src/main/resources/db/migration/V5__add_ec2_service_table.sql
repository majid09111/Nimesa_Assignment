CREATE TABLE IF NOT EXISTS `ec2_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_id` varchar(200) NOT NULL,
  `instance_id` VARCHAR(100) NOT NULL,
  `instance_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`job_id`) REFERENCES job_details (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;