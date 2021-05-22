FROM golang:alpine AS builder

WORKDIR /build
COPY . .

RUN go build .

FROM alpine:3.13.5

WORKDIR /app
COPY --from=builder /build/docky /app/docky
ENTRYPOINT ["/app/docky"]