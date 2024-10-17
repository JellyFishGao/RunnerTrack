package dev.andrewgao.RunnerTrack.user;


// "company": {
//     "name": "Romaguera-Crona",
//     "catchPhrase": "Multi-layered client-server neural-net",
//     "bs": "harness real-time e-markets"
//   }
public record Company (
    String name,
    String catchPhrase,
    String bs
){}
