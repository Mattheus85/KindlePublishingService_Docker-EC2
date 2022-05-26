# Kindle Publishing Service

## The Problem: Amazon Kindle Publishing

The Amazon Kindle store provides millions of ebooks to our customers. The process of publishing an
ebook to the Kindle catalog is currently an extremely manual process, which causes a long wait time
to add a book to the catalog.

As a member of the Amazon Kindle team, you will be launching a new service that allows our
publishing department to convert books into a digital format.

The overview, architecture, and implementation are covered in the [design document here](README.md). Almost all major pieces of software at Amazon first go through an intensive design
review to answer the question "Are we building the right thing for our customer?".

## Project Tasks

Up to this point, the services we have developed in projects have had synchronous APIs. This
means that a client makes a request to the service, all the work required to fulfill this request
is done, and then a response is returned to the client. In an asynchronous API, a client makes a
request, the service returns a response immediately, and the service completes the work after the
client disconnects. This is helpful when the work that needs to be done will take a long amount of
time. A client will only wait so long for a response, so it is helpful to quickly return a
successful response acknowledging the work is under way. The service will then continue to work on
the request as it continues to receive other, new requests. The service is working on these requests
concurrently - we can think of this as multitasking for now.

