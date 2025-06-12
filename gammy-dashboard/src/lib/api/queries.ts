import { useQueryClient } from "@tanstack/react-query";
import { useEffect } from "react";
import { $api } from "./clients";

export default function useLoginAsAdmin() {
  const queryClient = useQueryClient();

  useEffect(() => {
    fetch("http://localhost:8080/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      credentials: "include",
      body: JSON.stringify({
        username: "admin", password: "admin"
      })
    }).then(() => {
      queryClient.refetchQueries($api.queryOptions("get", "/user"))
    })
  });
}

export function useUserQuery() {
  return $api.useQuery("get", "/user");
}

export function usePlayersQuery() {
  const user = useUserQuery();

  return $api.useQuery("get", "/players", undefined, {
    enabled: user.isSuccess
  });
}

export function useGameStats() {
  const user = useUserQuery();

  return $api.useQuery("get", "/stats", undefined, {
    enabled: user.isSuccess
  });
}

export function useAchievements() {
  const user = useUserQuery();

  return $api.useQuery("get", "/achievements", undefined, {
    enabled: user.isSuccess
  });
}

export function useLeaderboards() {
  const user = useUserQuery();

  return $api.useQuery("get", "/leaderboards", undefined, {
    enabled: user.isSuccess
  });
}


export function useLeaderboardEntries(leaderboardApiName: string) {
  const user = useUserQuery();

  return $api.useQuery("get", "/leaderboards/{apiName}", { params: { path: { apiName: leaderboardApiName } } }, {
    enabled: user.isSuccess
  });
}

export function usePlayerReports() {
  const user = useUserQuery();

  return $api.useQuery("get", "/reports", undefined, {
    enabled: user.isSuccess
  });
}

export function useParameters() {
  const user = useUserQuery();

  return $api.useQuery("get", "/parameters", undefined, {
    enabled: user.isSuccess
  });
}

export function useInteractionTypes() {
  const user = useUserQuery();

  return $api.useQuery("get", "/interactions/type", undefined, {
    enabled: user.isSuccess
  });
}

export function useInteractions() {
  const user = useUserQuery();

  return $api.useQuery("get", "/interactions", undefined, {
    enabled: user.isSuccess
  });
}

export function usePlayerInteractions(playerId: number) {
  const user = useUserQuery();

  return $api.useQuery("get", "/interactions/player/{playerId}", {
    params: {
      path: { playerId }
    }
  }, {
    enabled: user.isSuccess
  });
}

export function usePlayerStats(playerId: number) {
  const user = useUserQuery();

  return $api.useQuery("get", "/stats/player/{playerId}", {
    params: {
      path: { playerId }
    },
  }, {
    enabled: user.isSuccess
  })
};


export function usePlayerStatsHistory(playerId: number) {
  const user = useUserQuery();

  return $api.useQuery("get", "/stats/player/{playerId}/history", {
    params: {
      path: { playerId }
    },
  }, {
    enabled: user.isSuccess
  })
};

export function usePlayerAchievements(playerId: number) {
  const user = useUserQuery();

  return $api.useQuery("get", "/achievements/player/{playerId}", {
    params: {
      path: { playerId }
    },
  }, {
    enabled: user.isSuccess
  })
}

export function usePlayerParameters(playerId: number) {
  const user = useUserQuery();

  return $api.useQuery("get", "/parameters/player/{playerId}", {
    params: {
      path: { playerId }
    },
  }, {
    enabled: user.isSuccess
  })
}

export function usePlayerParametersOverrides(playerId: number) {
  const user = useUserQuery();

  return $api.useQuery("get", "/parameters/overrides/{playerId}", {
    params: {
      path: { playerId }
    },
  }, {
    enabled: user.isSuccess
  })
}

export function useReportsFor(playerId: number) {
  const user = useUserQuery();

  return $api.useQuery("get", "/reports/for/{playerId}", {
    params: {
      path: { playerId }
    },
  }, {
    enabled: user.isSuccess
  })
}

export function useReportsBy(playerId: number) {
  const user = useUserQuery();

  return $api.useQuery("get", "/reports/by/{playerId}", {
    params: {
      path: { playerId }
    },
  }, {
    enabled: user.isSuccess
  })
}

export function usePlayer(playerId: number) {
  const user = useUserQuery();

  return $api.useQuery("get", "/players/{playerId}", {
    params: {
      path: { playerId }
    },
  }, {
    enabled: user.isSuccess
  })
}