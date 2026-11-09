from pathlib import Path
import subprocess
import tempfile
ROOT = Path(__file__).resolve().parent
with tempfile.TemporaryDirectory(prefix="coursework-check-") as build:
    sources = sorted((ROOT/"src").glob("*.java")) + sorted((ROOT/"tests").glob("*.java"))
    subprocess.run(["javac", "--release", "8", "-proc:none", "-encoding", "UTF-8", "-d", build, *map(str, sources)], check=True, timeout=30)
    subprocess.run(["java", "-Djava.awt.headless=true", "-cp", build, "RecommenderTest"], check=True, timeout=30)
