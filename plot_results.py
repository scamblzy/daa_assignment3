
import pandas as pd
import matplotlib.pyplot as plt
from pathlib import Path

# Project folders
BASE_DIR = Path(__file__).parent

CSV_PATH = BASE_DIR / "3asik" / "results" / "benchmark.csv"

PLOTS_DIR = BASE_DIR / "3asik" / "results" / "plots"


PLOTS_DIR.mkdir(parents=True, exist_ok=True)

# Load benchmark results
df = pd.read_csv(CSV_PATH)

# Convert nanoseconds to milliseconds
df["avg_time_ms"] = df["avg_time_ns"] / 1_000_000

# Create both required plots
plots = [
    ("vertices", "Number of Vertices (V)",
     "execution_time_vs_vertices.png"),
    ("edges", "Number of Edges (E)",
     "execution_time_vs_edges.png"),
]

for x_column, x_label, filename in plots:
    fig, ax = plt.subplots(figsize=(9, 5))

    for algorithm, group in df.groupby("algorithm"):
        group = group.sort_values(x_column)
        ax.plot(
            group[x_column],
            group["avg_time_ms"],
            marker="o",
            label=algorithm
        )

    ax.set_xlabel(x_label)
    ax.set_ylabel("Average Execution Time (ms)")
    ax.set_title(f"Execution Time vs {x_label}")
    ax.legend()
    ax.grid(True)
    fig.tight_layout()

    fig.savefig(PLOTS_DIR / filename, dpi=300)
    plt.show()
    plt.close(fig)

print("Both plots saved successfully!")
