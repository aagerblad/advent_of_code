class cube:
    def __init__(self, typee, edges):
        self.typee = typee
        self.start_edges = [edges[0], edges[2], edges[4]]
        self.end_edges = [edges[1], edges[3], edges[5]]
