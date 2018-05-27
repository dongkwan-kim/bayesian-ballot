# bayesian-ballot
Inference of the probability distribution of the ballot in Congress.

## Data Format

### vote_result.csv

| bill_no | 金成泰 | 강길부 | 강병원 | 강석진 | 강석호 | 강창일 | 강효상 | ... |
|---------|--------|--------|--------|--------|--------|--------|--------|-----|
| 2013661 | pro    | pro    | pro    | pro    | pro    | abs    | abs    |     |
| 2013660 | pro    | pro    | pro    | pro    | pro    | abs    | abs    |     |
| ...     |        |        |        |        |        |        |        |     |

- pro: 찬성
- con: 반대
- abs(absent): 불참
- wdr(withdraw): 기권

### vote_distance.csv

| P1     | P2     | Cosine Similarity  |
|--------|--------|--------------------|
| ...    |        |                    |
| 이은권 | 홍문종 | 0.5120726400156858 |
| 김성찬 | 홍문종 | 0.5125520208924266 |
| 진영   | 홍문종 | 0.5142801095736699 |
| ...    |        |                    |
