import pika
import json

# credentials = pika.PlainCredentials(username='guest', password='guest')
# connection = pika.BlockingConnection(pika.ConnectionParameters('localhost', 5672, '#vhostname', credentials))
connection = pika.BlockingConnection(pika.ConnectionParameters('localhost'))
channel = connection.channel()
channel.queue_declare(queue='login-request-queue', durable=True)
message = {
    'uuid':'e58ed763-928c-4155-bee9-fdbaaadc15f3',
    'password': 'password1',
    'username': 'user',
}
message_json = json.dumps(message)
channel.basic_publish(exchange='login-topic',
                      routing_key='login.request',
                      # properties=pika.BasicProperties(content_type='application/json'),
                      # body=message_json
                      body=str(message),
                      properties=pika.BasicProperties(content_type='text/plain')
                      )
connection.close()
print("Sending Done")