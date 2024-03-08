# uscis-notifier
## Description
This application checks the USCIS (U.S. Citizenship and Immigration Services) Case Status Check API hourly and if there's a change sends an email about case status change.
I created this application to reduce my personal anxieties and check the case status multiple times per day as USCIS does not provide reliable updates on cases.

Email implementation is tested with Gmail, but any other SMTP Email service should work

## Configuration

### Subscriptions

Example for defining Email subscriptions and cases in Application Properties:

```yaml
notification:
  subscriptions:
  - recipients:
      - any@email.com
    cases:
      - IOE0000001234
      - IOE0000001235
```
Docker environment variables:

```
NOTIFICATION_SUBSCRIPTIONS_0_RECIPIENTS_0=any@email.com
NOTIFICATION_SUBSCRIPTIONS_0_CASES_0=IOE0000001234
NOTIFICATION_SUBSCRIPTIONS_0_CASES_1=IOE0000001235
```
### Database

The cases are stored in a sqlite database. Feel free to configure any other JPA data provider.
If you use sqlite make sure to create a volume to make it persistent. See the application properties file for configuration options.

### SMTP Service

Check out the application properties for configuration options.
The email template is at 



