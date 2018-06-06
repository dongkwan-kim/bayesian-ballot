# bayesian-ballot

On May 21, a motion to arrest Hong Moon-chong and Yeom Dong-Yeol, members of the Jayuhangug Party,
was rejected by the National Assembly. The National Assembly rejected the proposal for arresting Hong, who
received embezzlement and bribery charges, with 129 consents, 141 dissents, 2 withdrawals and 3 absences.
Yeom, who has been charged with abuse of power and obstructing his duties, has also voted against the bill
with 98 consents, 172 dissents, 1 withdrawals and 4 absences. Since the Jayuhangug party has only 113 seats,
it is presumed that a lot of 'sympathy signs' for fellow lawmakers have come out regardless of the political
affiliation, especially from Deobuleominju, which is the ruling party. We want to find the 'traitors' from the
Deobuleominju party, but the vote was conducted under anonymity, so we can't find out them explicitly.
However, National Assembly stores ballot results of every open votes in their own database and any people
can access it. Thus we decided to find out 'traitors' of the secret votes based on the ballot data of 20th
(current) National Assembly.

First, we constructed a model about the consistency of vote results between two members, one for our key
member (Hong or Yeom), the other for each remaining members who attended at the day. Then we inferred
the probability to vote same as each key member in open votes for all members, and based on this, we finally
inferred the probability to vote same as each key member in secret votes. Although we cannot verify our
results, we found some interesting features in our project. We used Interacting Particle Markov Chain Monte
Carlo inference algorithm for every [Anglican](https://github.com/probprog/anglican) query.

## Disclaimer

This work was done as part of Probabilistic Programming (KAIST CS492) project.
All the names are anonymized and the results only work based on our strong assumptions.
