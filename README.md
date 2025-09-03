# LLM Usage Tracker API

An API for tracking and analyzing Large Language Model (LLM) usage across multiple vendors, models, and API types.  
It provides endpoints to collect usage data, calculate costs, monitor revenue and profit margins, and generate growth metrics over time.

## Setting up

### Running db container

Run the command below to create a new empty docker db instance:

```bash
docker run --name llm-usage-tracker-db \
-e MARIADB_ROOT_PASSWORD=admin \
-e MARIADB_DATABASE=llm-usage-tracker-db \
-e MARIADB_USER=admin \
-e MARIADB_PASSWORD=admin \
-p 3399:3306 \
-d mariadb:11
```

`application.yml` already contains the credentials and url string to connect to the db instance above.

### Populating db with data

Populate `vendor_pricing` table with some random pricing values:

```sql
INSERT INTO `vendor_pricing` (`id`, `metric`, `model_name`, `price_per_unit`, `vendor_name`) VALUES (1, 'input_tokens', 'gpt-4', 0.05, 'OpenAI');
INSERT INTO `vendor_pricing` (`id`, `metric`, `model_name`, `price_per_unit`, `vendor_name`) VALUES (2, 'output_tokens', 'gpt-4', 0.10, 'OpenAI');
INSERT INTO `vendor_pricing` (`id`, `metric`, `model_name`, `price_per_unit`, `vendor_name`) VALUES (3, 'images', 'dall-e', 0.02, 'OpenAI');
INSERT INTO `vendor_pricing` (`id`, `metric`, `model_name`, `price_per_unit`, `vendor_name`) VALUES (4, 'audio_minutes', 'whisper', 0.01, 'OpenAI');
INSERT INTO `vendor_pricing` (`id`, `metric`, `model_name`, `price_per_unit`, `vendor_name`) VALUES (5, 'video_minutes', 'gpt-4', 0.50, 'OpenAI');
INSERT INTO `vendor_pricing` (`id`, `metric`, `model_name`, `price_per_unit`, `vendor_name`) VALUES (6, 'input_tokens', 'claude-v1', 0.03, 'Anthropic');
INSERT INTO `vendor_pricing` (`id`, `metric`, `model_name`, `price_per_unit`, `vendor_name`) VALUES (7, 'output_tokens', 'claude-v1', 0.03, 'Anthropic');
INSERT INTO `vendor_pricing` (`id`, `metric`, `model_name`, `price_per_unit`, `vendor_name`) VALUES (8, 'images', 'claude-image', 0.03, 'Anthropic');
INSERT INTO `vendor_pricing` (`id`, `metric`, `model_name`, `price_per_unit`, `vendor_name`) VALUES (9, 'audio_minutes', 'claude-audio', 0.20, 'Anthropic');
INSERT INTO `vendor_pricing` (`id`, `metric`, `model_name`, `price_per_unit`, `vendor_name`) VALUES (10, 'video_minutes', 'claude-video', 0.02, 'Anthropic');
INSERT INTO `vendor_pricing` (`id`, `metric`, `model_name`, `price_per_unit`, `vendor_name`) VALUES (11, 'input_tokens', 'gemini-1', 0.04, 'Gemini');
INSERT INTO `vendor_pricing` (`id`, `metric`, `model_name`, `price_per_unit`, `vendor_name`) VALUES (12, 'images', 'gemini-1', 0.03, 'Gemini');
INSERT INTO `vendor_pricing` (`id`, `metric`, `model_name`, `price_per_unit`, `vendor_name`) VALUES (13, 'video_minutes', 'gemini-1', 0.02, 'Gemini');
INSERT INTO `vendor_pricing` (`id`, `metric`, `model_name`, `price_per_unit`, `vendor_name`) VALUES (14, 'audio_minutes', 'gemini-1', 0.01, 'Gemini');
```

Populate `customer_pricing` table with some random pricing values:
```sql
INSERT INTO `customer_pricing` (`id`, `customer_id`, `markup_percent`) VALUES (1, 'customer1', 0.3);
INSERT INTO `customer_pricing` (`id`, `customer_id`, `markup_percent`) VALUES (2, 'customer2', 0.2);
INSERT INTO `customer_pricing` (`id`, `customer_id`, `markup_percent`) VALUES (3, 'customer3', 0.5);
```

Populate `usage_metrics` table with some random usage events:
```sql
INSERT INTO `usage_metric` (`id`, `audio_minutes`, `cached_tokens`, `customer_id`, `input_tokens`, `number_of_images`, `org_name`, `output_tokens`, `timestamp`, `total_usage`, `user_email`, `user_id`, `vendor_api_type`, `vendor_model`, `vendor_name`, `video_minutes`) VALUES (1, 3, 1, 'customer1', 10, 2, 'org1', 10, '2025-08-30 15:00:00.000000', 26, 'nora@example.com', 'user1', 'chat', 'gpt-4', 'OpenAI', 0);
INSERT INTO `usage_metric` (`id`, `audio_minutes`, `cached_tokens`, `customer_id`, `input_tokens`, `number_of_images`, `org_name`, `output_tokens`, `timestamp`, `total_usage`, `user_email`, `user_id`, `vendor_api_type`, `vendor_model`, `vendor_name`, `video_minutes`) VALUES (2, 3, 2, 'customer1', 50, 2, 'org1', 50, '2025-08-30 15:00:00.000000', 107, 'nora@example.com', 'user1', 'chat', 'gpt-4', 'OpenAI', 0);
INSERT INTO `usage_metric` (`id`, `audio_minutes`, `cached_tokens`, `customer_id`, `input_tokens`, `number_of_images`, `org_name`, `output_tokens`, `timestamp`, `total_usage`, `user_email`, `user_id`, `vendor_api_type`, `vendor_model`, `vendor_name`, `video_minutes`) VALUES (3, 2, 3, 'customer2', 20, 1, 'org2', 50, '2025-08-30 22:44:05.000000', 77, 'bob@gmail.com', 'user2', 'completion', 'claude-3', 'Anthropic', 1);
INSERT INTO `usage_metric` (`id`, `audio_minutes`, `cached_tokens`, `customer_id`, `input_tokens`, `number_of_images`, `org_name`, `output_tokens`, `timestamp`, `total_usage`, `user_email`, `user_id`, `vendor_api_type`, `vendor_model`, `vendor_name`, `video_minutes`) VALUES (4, 4, NULL, 'customer2', 10, 10, 'org2', 5, '2025-09-30 22:52:13.000000', 31, 'lum@gmail.com', 'user3', 'completion', 'claude-3', 'Anthropic', 2);
INSERT INTO `usage_metric` (`id`, `audio_minutes`, `cached_tokens`, `customer_id`, `input_tokens`, `number_of_images`, `org_name`, `output_tokens`, `timestamp`, `total_usage`, `user_email`, `user_id`, `vendor_api_type`, `vendor_model`, `vendor_name`, `video_minutes`) VALUES (5, 10, 4, 'customer3', 0, 20, 'org3', 0, '2025-09-21 21:57:05.000000', 34, 'bill@gmail.com', 'user4', 'generation', 'gemini-pro', 'Gemini', 0);
INSERT INTO `usage_metric` (`id`, `audio_minutes`, `cached_tokens`, `customer_id`, `input_tokens`, `number_of_images`, `org_name`, `output_tokens`, `timestamp`, `total_usage`, `user_email`, `user_id`, `vendor_api_type`, `vendor_model`, `vendor_name`, `video_minutes`) VALUES (6, 5, 5, 'customer1', 0, 0, 'org1', 0, '2025-05-30 22:03:06.000000', 10, 'bob@gmail.com', 'user2', 'generation', 'gemini-pro', 'Gemini', 0);
INSERT INTO `usage_metric` (`id`, `audio_minutes`, `cached_tokens`, `customer_id`, `input_tokens`, `number_of_images`, `org_name`, `output_tokens`, `timestamp`, `total_usage`, `user_email`, `user_id`, `vendor_api_type`, `vendor_model`, `vendor_name`, `video_minutes`) VALUES (7, 0, 6, 'customer3', 0, 0, 'org3', 0, '2025-10-10 22:05:16.000000', 16, 'lum@gmail.com', 'user3', 'generation', 'gpt-5', 'OpenAI', 10);
INSERT INTO `usage_metric` (`id`, `audio_minutes`, `cached_tokens`, `customer_id`, `input_tokens`, `number_of_images`, `org_name`, `output_tokens`, `timestamp`, `total_usage`, `user_email`, `user_id`, `vendor_api_type`, `vendor_model`, `vendor_name`, `video_minutes`) VALUES (8, 0, NULL, 'customer2', 12, 0, 'org2', 15, '2025-08-20 22:13:30.000000', 17, 'bill@gmail.com', 'user4', 'chat', 'claude-3', 'Anthropic', 0);
```


### Running the service

Run the API using the command below:
```bash
./gradlew bootrun
```

### Curl requests to store usage metrics:

Make the curl request below to create a usage event for OpenAI vendor:

```bash
curl -X POST http://localhost:9000/usage \
  -H "Content-Type: application/json" \
  -d '{
    "customer": {
      "id": "customer1",
      "orgName": "org1"
    },
    "user": {
      "id": "user1",
      "email": "alice@example.com"
    },
    "vendor": {
      "name": "OpenAI",
      "model": "gpt-4",
      "apiType": "chat"
    },
    "usage": {
      "promptTokens": 500,
      "completionTokens": 1000,
      "numberOfImages": 2,
      "videoDuration": 0,
      "audioDuration": 3.5
    },
    "timestamp": "2025-08-30T15:00:00Z"
  }'
```

Make the curl request below to create a usage event for Anthropic vendor:

```bash
curl -X POST http://localhost:9000/usage \
  -H "Content-Type: application/json" \
  -d '{
    "customer": {
      "customerId": "customer1",
      "orgName": "Bob"
    },
    "user": {
      "userId": "user1",
      "userEmail": "bob@example.com"
    },
    "vendor": {
      "vendorName": "Anthropic",
      "vendorModel": "claude-3",
      "vendorApiType": "completion"
    },
    "usage": {
      "inputTokensRaw": 800,
      "outputTokensRaw": 1200,
      "imagesRaw": 1,
      "videoDuration": 0,
      "audioDuration": 0
    },
    "timestamp": "2025-08-30T16:00:00Z"
  }'
```

Make the curl request below to create a usage event for Gemini vendor:

```bash
curl -X POST http://localhost:9000/usage \
  -H "Content-Type: application/json" \
  -d '{
    "customer": {
      "customerId": "customer2",
      "orgName": "Charlie"
    },
    "user": {
      "userId": "user2",
      "userEmail": "charlie@example.com"
    },
    "vendor": {
      "vendorName": "Gemini",
      "vendorModel": "gemini-1",
      "vendorApiType": "chat"
    },
    "usage": {
      "textTokens": 400,
      "nrImages": 0,
      "videoLengthInMinutes": 10,
      "audioLengthInMinutes": 7.2
    },
    "timestamp": "2025-08-30T17:00:00Z"
  }'
```

### Endpoint to get usage metrics:

1. Get all metrics:
```curl
GET http://localhost:9000/metrics/summary
```

2. Find by specific customer:
```curl
GET http://localhost:9000/metrics/summary?customerId=cust111
```

3. Filter by customer and user:
```curl
GET http://localhost:9000/metrics/summary?customerId=cust111&userId=user222
```

4. Filter by vendor:
```curl
GET http://localhost:9000/metrics/summary?vendorName=OpenAI
```

5. Filter by API type:
```curl
GET http://localhost:9000/metrics/summary?apiType=gpt-4
```

6. Filter by date range:
```curl
GET http://localhost:9000/metrics/summary?fromDate=2025-08-01&toDate=2025-08-31 
```
7. Group by vendor name
```curl
GET http://localhost:9000/metrics/grouped?groupBy=vendorName&period=month
```
8. Group by vendor api type
```curl
GET http://localhost:9000/metrics/grouped?groupBy=vendorApiType&period=day
```
9. Group by vendor model
```curl
GET http://localhost:9000/metrics/grouped?groupBy=vendorModel&period=week
```
