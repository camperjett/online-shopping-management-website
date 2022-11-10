CREATE TABLE IF NOT EXISTS
  `user` (
    `id` int unsigned NOT NULL AUTO_INCREMENT,
    `username` varchar(255) NOT NULL,
    `password` varchar(255) DEFAULT NULL,
    `role` int unsigned NOT NULL DEFAULT 0,
    `date_joined` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
  );

CREATE TABLE IF NOT EXISTS
  `customer` (
  `cid` int unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int unsigned,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `gender` tinyint DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone_no` varchar(255) DEFAULT NULL,
  `photo_url` varchar(255) DEFAULT NULL,
   PRIMARY KEY (`cid`),
   FOREIGN KEY (`user_id`) REFERENCES user(`id`) ON DELETE CASCADE ON UPDATE CASCADE
  );

  CREATE TABLE IF NOT EXISTS
    `shopkeeper` (
    `sid` int unsigned NOT NULL AUTO_INCREMENT,
    `user_id` int unsigned,
    `first_name` varchar(255) NOT NULL,
    `last_name` varchar(255) DEFAULT NULL,
    `gender` tinyint DEFAULT NULL,
    `email` varchar(255) DEFAULT NULL,
    `phone_no` varchar(255) DEFAULT NULL,
    `photo_url` varchar(255) DEFAULT NULL,
    `license_image_url` varchar(255) DEFAULT NULL,
    `license_no` varchar(255) DEFAULT NULL,
     PRIMARY KEY (`sid`),
     FOREIGN KEY (`user_id`) REFERENCES user(`id`) ON DELETE CASCADE ON UPDATE CASCADE
    );
CREATE TABLE IF NOT EXISTS
    `admin` (
    `aid` int unsigned NOT NULL AUTO_INCREMENT,
    `user_id` int unsigned,
    `first_name` varchar(255) NOT NULL,
    `last_name` varchar(255) DEFAULT NULL,
    `gender` tinyint DEFAULT NULL,
    `email` varchar(255) DEFAULT NULL,
    `phone_no` varchar(255) DEFAULT NULL,
    `photo_url` varchar(255) DEFAULT NULL,
     PRIMARY KEY (`aid`),
     FOREIGN KEY (`user_id`) REFERENCES user(`id`) ON DELETE CASCADE ON UPDATE CASCADE
    );
CREATE TABLE IF NOT EXISTS
`user_contact` (
`ucid` int unsigned NOT NULL AUTO_INCREMENT,
`contact_no` varchar(50) DEFAULT NULL,
`user_id` int unsigned DEFAULT NULL,
PRIMARY KEY (`ucid`),
FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS
  `user_email` (
    `ueid` int unsigned NOT NULL AUTO_INCREMENT,
    `email` varchar(50) DEFAULT NULL,
    `user_id` int unsigned DEFAULT NULL,
    PRIMARY KEY (`ueid`),
    KEY `user_id` (`user_id`),
    CONSTRAINT `user_email_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
  ) ;
CREATE TABLE IF NOT EXISTS
  `user_address` (
    `address_id` int NOT NULL AUTO_INCREMENT,
    `pincode` varchar(10) DEFAULT NULL,
    `house_address` varchar(100) DEFAULT NULL,
    `city` varchar(30) DEFAULT NULL,
    `state` int DEFAULT NULL,
    `user_id` int unsigned DEFAULT NULL,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`address_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
  );


CREATE TABLE IF NOT EXISTS
  `orders` (
    `order_id` int NOT NULL AUTO_INCREMENT,
    `status` int DEFAULT NULL,
    `date` timestamp DEFAULT NULL,
    `user_id` int unsigned DEFAULT NULL,
    `address_id` int DEFAULT NULL,
    `transaction_id` int DEFAULT NULL,
    PRIMARY KEY (`order_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`address_id`) REFERENCES `user_address`(`address_id`) ON DELETE CASCADE
  );

CREATE TABLE IF NOT EXISTS
  `category` (
    `cid` int unsigned NOT NULL AUTO_INCREMENT,
    `title` varchar(255) DEFAULT NULL,
    `image_path` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`cid`)
  );
  CREATE TABLE IF NOT EXISTS
 `Product` (
        `product_id` int auto_increment,
        `product_name` varchar(100),
        `date_added` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
        `avg_rating` decimal(5,2) DEFAULT 0,
        `no_of_reviews` int DEFAULT 0,
        `stock` int DEFAULT 0,
        `description` varchar(1000),
        `MRP` int,
        `brand` varchar(50),
        `category_id` int unsigned NOT NULL,
        `photo_url` varchar(255) DEFAULT NULL,
        PRIMARY KEY (`product_id`),
        FOREIGN KEY (`category_id`) REFERENCES category(`cid`) ON DELETE CASCADE ON UPDATE CASCADE
    );

CREATE TABLE IF NOT EXISTS `Review` (
  `user_id` int unsigned,
  `product_id` int,
  `title` varchar(255) DEFAULT NULL,
  `text` varchar(500) DEFAULT NULL,
  `ratings` FLOAT DEFAULT 0,
  `upVotes` int DEFAULT 0,
  `downVotes` int DEFAULT 0,
  `review_id` int AUTO_INCREMENT,
  PRIMARY KEY (`review_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`product_id`) REFERENCES `Product`(`product_id`) ON DELETE CASCADE ON UPDATE CASCADE
  );

-- CREATE TABLE IF NOT EXISTS
--  `review` (
--    `user_id` int unsigned NOT NULL,
--    `product_id` int NOT NULL,
--    `title` varchar(50) DEFAULT NULL,
--    `text` varchar(500) DEFAULT NULL,
--    `rating` varchar(20) DEFAULT NULL,
--    `review_id` int NOT NULL,
--    PRIMARY KEY (`review_id),
--    KEY `product_id` (`product_id`),
--    CONSTRAINT `review_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
--    CONSTRAINT `review_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `Product` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE
--  )


CREATE TABLE IF NOT EXISTS
`cart` (
  cart_id int not null auto_increment,
`user_id` int unsigned NOT NULL,
`product_id` int NOT NULL,
`quantity` int NOT NULL,
PRIMARY KEY (cart_id),
KEY `product_id` (`product_id`),
CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `Product` (`product_id`) ON DELETE CASCADE,
CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS
    `shopkeeperRequest` (
    `sid` int unsigned NOT NULL AUTO_INCREMENT,
    `user_id` int unsigned,
    `first_name` varchar(255) NOT NULL,
    `last_name` varchar(255) DEFAULT NULL,
    `gender` tinyint DEFAULT NULL,
    `email` varchar(255) DEFAULT NULL,
    `phone_no` varchar(255) DEFAULT NULL,
    `photo_url` varchar(255) DEFAULT NULL,
    `license_image_url` varchar(255) DEFAULT NULL,
    `license_no` varchar(255) DEFAULT NULL,
    `isApproved` int DEFAULT NULL,
     PRIMARY KEY (`sid`),
     FOREIGN KEY (`user_id`) REFERENCES user(`id`) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS
    `feedback` (
        `feed_id` int unsigned NOT NULL AUTO_INCREMENT,
        `message` varchar(1000) DEFAULT NULL,
        `reply` varchar(1000) DEFAULT NULL,
        `feed_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
        `reviewed_by` int unsigned DEFAULT NULL,
        `user_id` int unsigned DEFAULT NULL,
        PRIMARY KEY (`feed_id`),
        FOREIGN KEY (`reviewed_by`) REFERENCES admin(`aid`) ON DELETE CASCADE,
        FOREIGN KEY (`user_id`) REFERENCES user(`id`) ON DELETE CASCADE
    );

  CREATE TABLE IF NOT EXISTS
    `transaction` (
        `transaction_id` int unsigned NOT NULL AUTO_INCREMENT,
        `date_of_tran` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
        `amount` int DEFAULT NULL,
        `mode` int DEFAULT NULL,
        `account_no` varchar(100) DEFAULT NULL,
        `bank_name` varchar(200) DEFAULT NULL,
        `bank_branch` varchar(200) DEFAULT NULL,
        `verification_status` int DEFAULT 0,
      	`user_id` int unsigned DEFAULT NULL,
      	PRIMARY KEY (`transaction_id`),
      	FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
    );


CREATE TABLE IF NOT EXISTS
  `order_details` (
    `oid` int NOT NULL AUTO_INCREMENT,
    `order_id` int NOT NULL,
    `product_id` int NOT NULL,
    `final_price` int DEFAULT NULL,
    `quantity` int DEFAULT NULL,
    PRIMARY KEY (`oid`),
    FOREIGN KEY (`order_id`) REFERENCES `orders`(`order_id`) ON DELETE CASCADE,
    FOREIGN KEY (`product_id`) REFERENCES `Product`(`product_id`) ON DELETE CASCADE
  );



CREATE TABLE IF NOT EXISTS
    `wishlist` (
        `wid` int unsigned NOT NULL AUTO_INCREMENT,
        `product_id` int  NOT NULL,
        `user_id` int unsigned DEFAULT NULL,
        PRIMARY KEY (`wid`),
        FOREIGN KEY (`product_id`) REFERENCES Product(`product_id`) ON DELETE CASCADE,
        FOREIGN KEY (`user_id`) REFERENCES user(`id`) ON DELETE CASCADE
    );



  CREATE TABLE IF NOT EXISTS
 `image` (
        `id` int auto_increment,
        `bloby` BLOB,
        PRIMARY KEY (`id`)
    );

      CREATE TABLE IF NOT EXISTS
 `Product_pics` (
        `id` int auto_increment,
   		`product_id` int,
        `bloby` BLOB,
        PRIMARY KEY (`id`),
   		FOREIGN KEY (`product_id`) REFERENCES `Product`(`product_id`) ON DELETE CASCADE ON UPDATE CASCADE
    );

       CREATE TABLE IF NOT EXISTS
  `Cat_pics` (
         `id` int auto_increment,
    	`product_id` int unsigned,
         `bloby` BLOB,
         PRIMARY KEY (`id`),
    		FOREIGN KEY (`product_id`) REFERENCES `category`(`cid`) ON DELETE CASCADE ON UPDATE CASCADE
     );
