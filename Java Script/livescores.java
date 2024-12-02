document.addEventListener("DOMContentLoaded", () => {
    const apiKey = "a56d61a0701c57e941e3"; 
    const scoresContainer = document.getElementById("scores-container");

    async function fetchLiveScores() {
        try {
            const response = await fetch(
                "https://v3.football.api-sports.io/fixtures?live=all",
                {
                    headers: {
                        "x-rapidapi-host": "v3.football.api-sports.io",
                        "x-rapidapi-key": apiKey,
                    },
                }
            );

            if (!response.ok) {
                throw new Error("Failed to fetch live scores");
            }

            const data = await response.json();
            displayLiveScores(data.response);
        } catch (error) {
            console.error(error);
            scoresContainer.innerHTML = "<p>Error loading live scores. Please try again later.</p>";
        }
    }

    function displayLiveScores(scores) {
        scoresContainer.innerHTML = ""; // Clear placeholder content

        if (scores.length === 0) {
            scoresContainer.innerHTML = "<p>No live matches right now.</p>";
            return;
        }

        scores.forEach((match) => {
            const matchCard = document.createElement("div");
            matchCard.className = "match-card";

            const homeTeam = match.teams.home.name;
            const awayTeam = match.teams.away.name;
            const homeScore = match.goals.home !== null ? match.goals.home : "-";
            const awayScore = match.goals.away !== null ? match.goals.away : "-";

            matchCard.innerHTML = `
                <p><strong>${homeTeam}</strong> vs <strong>${awayTeam}</strong></p>
                <p>Score: ${homeScore} - ${awayScore}</p>
                <p>Status: ${match.fixture.status.long}</p>
            `;

            scoresContainer.appendChild(matchCard);
        });
    }

    fetchLiveScores();
});
