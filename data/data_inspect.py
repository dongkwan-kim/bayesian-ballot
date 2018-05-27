import csv
from scipy import spatial


FILE_NAME = 'vote_result_1521.csv'


class Reader:

    def __init__(self, file_name):
        self.f = open(file_name, 'r', encoding='utf-8')
        self.r = list(csv.DictReader(self.f))

    def encode(self, v):
        encoding = {'pro': 1, 'con': -1, 'wdr': 0, 'abs': 0}
        return encoding[v]

    def get_col(self, name) -> list:
        col = []
        for row in self.r:
            col.append(self.encode(row[name]))
        return col

    def get_field_names(self) -> list:
        return list(self.r[0].keys())

    def get_members(self) -> list:
        return self.get_field_names()[1:]

    def get_member_pairs(self, center_members):
        members = self.get_members()
        return list(set([tuple(sorted([x, y])) for x in members for y in members
                         if x != y and (not center_members or x in center_members or y in center_members)]))

    def get_distance(self, p1: str, p2: str, metric=None):

        if not metric:
            metric = lambda x, y: 1 - spatial.distance.cosine(x, y)

        vec_1 = self.get_col(p1)
        vec_2 = self.get_col(p2)

        return metric(vec_1, vec_2)

    def close(self):
        self.f.close()


def write_distance_csv(center_members=None):

    output_file = 'vote_distance{}.csv'.format(
        '_'.join([''] + center_members if center_members else list())
    )
    reader = Reader(FILE_NAME)

    member_pairs = reader.get_member_pairs(center_members)
    pairs_to_dist = ((t, reader.get_distance(t[0], t[1])) for t in member_pairs)
    sorted_pairs_to_dist = sorted([x for x in pairs_to_dist if str(x[1]) != 'nan'],
                                  key=lambda x: x[1])

    with open(output_file, 'w', encoding='utf-8') as f:
        for pair, dist in sorted_pairs_to_dist:
            f.write(','.join(list(pair) + [str(dist), '\n']))
        f.close()
    reader.close()


if __name__ == '__main__':
    write_distance_csv()
