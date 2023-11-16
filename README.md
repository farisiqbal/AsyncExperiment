# AsyncExperiment

This is a POC app for experimentation implementation which needs requires these things to work:
- Client SDK initialized
- Customer data loaded
Which we will do asynchronously during app launch.

Such process could potentially take some time to complete while we may already make some call before it's completion.
In this example, we added delays to both processes of initializing the Client SDK and loading customer's data.
However, during that delay, the user may already navigated to the next screen (NextActivity) which has an experiment.
