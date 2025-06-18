import {useQueryClient} from "@tanstack/react-query";
import {$api} from "./clients";

export function useUpdatePlayerMutation(playerId: number) {
    const queryClient = useQueryClient();

    return $api.useMutation("put", "/players", {
        onSettled() {
            queryClient.refetchQueries(
                $api.queryOptions("get", "/players/{playerId}", {params: {path: {playerId}}})
            )
            queryClient.refetchQueries(
                $api.queryOptions("get", "/players")
            )
        }
    });

}

export function useUnlockPlayerAchievement(playerId: number) {
    const queryClient = useQueryClient();

    return $api.useMutation("post", "/achievements/player/{playerId}/{apiName}", {
        onSettled() {
            queryClient.refetchQueries(
                $api.queryOptions("get", "/achievements/player/{playerId}", {params: {path: {playerId}}})
            )
        }
    });

}

export function useLockPlayerAchievement(playerId: number) {
    const queryClient = useQueryClient();

    return $api.useMutation("delete", "/achievements/player/{playerId}/{apiName}", {
        onSettled() {
            queryClient.refetchQueries(
                $api.queryOptions("get", "/achievements/player/{playerId}", {params: {path: {playerId}}})
            )
        }
    });
}

export function useCreatePlayerParameterOverrideMutation(playerId: number) {
    const queryClient = useQueryClient();

    return $api.useMutation("post", "/parameters/overrides", {
        onSettled() {
            queryClient.refetchQueries(
                $api.queryOptions("get", "/parameters/overrides/{playerId}", {params: {path: {playerId}}})
            )
            queryClient.refetchQueries(
                $api.queryOptions("get", "/parameters/player/{playerId}", {params: {path: {playerId}}})
            )
        }
    });
}

export function useDeletePlayerParameterOverrideMutation(playerId: number) {
    const queryClient = useQueryClient();

    return $api.useMutation("delete", "/parameters/overrides", {
        onSettled() {
            queryClient.refetchQueries(
                $api.queryOptions("get", "/parameters/overrides/{playerId}", {params: {path: {playerId}}})
            )
            queryClient.refetchQueries(
                $api.queryOptions("get", "/parameters/player/{playerId}", {params: {path: {playerId}}})
            )
        }
    });
}

export function useUpdatePlayerParameterOverrideMutation(playerId: number) {
    const queryClient = useQueryClient();

    return $api.useMutation("put", "/parameters/overrides", {
        onSettled() {
            queryClient.refetchQueries(
                $api.queryOptions("get", "/parameters/overrides/{playerId}", {params: {path: {playerId}}})
            )
            queryClient.refetchQueries(
                $api.queryOptions("get", "/parameters/player/{playerId}", {params: {path: {playerId}}})
            )
        }
    });
}

export function useCreateAchievementMutation() {
    const queryClient = useQueryClient();

    return $api.useMutation("post", "/achievements", {
        onSettled() {
            queryClient.refetchQueries(
                $api.queryOptions("get", "/achievements")
            )
        }
    });
}

export function useDeleteAchievementMutation() {
    const queryClient = useQueryClient();

    return $api.useMutation("delete", "/achievements", {
        onSettled() {
            queryClient.refetchQueries(
                $api.queryOptions("get", "/achievements")
            )
        }
    });
}

export function useUpdateAchievementMutation() {
    const queryClient = useQueryClient();

    return $api.useMutation("put", "/achievements", {
        onSettled() {
            queryClient.refetchQueries(
                $api.queryOptions("get", "/achievements")
            )
        }
    });
}

export function useCreateGameStat() {
    const queryClient = useQueryClient();

    return $api.useMutation("post", "/stats", {
        onSettled() {
            queryClient.refetchQueries(
                $api.queryOptions("get", "/stats")
            )
        }
    });
}

export function useDeleteGameStat() {
    const queryClient = useQueryClient();

    return $api.useMutation("delete", "/stats", {
        onSettled() {
            queryClient.refetchQueries(
                $api.queryOptions("get", "/stats")
            )
        }
    });
}

export function useUpdateGameStat() {
    const queryClient = useQueryClient();

    return $api.useMutation("put", "/stats", {
        onSettled() {
            queryClient.refetchQueries(
                $api.queryOptions("get", "/stats")
            )
        }
    });
}

export function useCreateLeaderboard() {
    const queryClient = useQueryClient();

    return $api.useMutation("post", "/leaderboards", {
        onSettled() {
            queryClient.refetchQueries(
                $api.queryOptions("get", "/leaderboards")
            )
        }
    });
}

export function useDeleteLeaderboard() {
    const queryClient = useQueryClient();

    return $api.useMutation("delete", "/leaderboards", {
        onSettled() {
            queryClient.refetchQueries(
                $api.queryOptions("get", "/leaderboards")
            )
        }
    });
}

export function useUpdateLeaderboard() {
    const queryClient = useQueryClient();

    return $api.useMutation("put", "/leaderboards", {
        onSettled() {
            queryClient.refetchQueries(
                $api.queryOptions("get", "/leaderboards")
            )
        }
    });
}

export function useDeleteInteractionType() {
    const queryClient = useQueryClient();

    return $api.useMutation("delete", "/interactions/type", {
        onSettled() {
            queryClient.refetchQueries(
                $api.queryOptions("get", "/interactions/type")
            )
        }
    });
}

export function useCreateInteractionType() {
    const queryClient = useQueryClient();

    return $api.useMutation("post", "/interactions/type", {
        onSettled() {
            queryClient.refetchQueries(
                $api.queryOptions("get", "/interactions/type")
            )
        }
    });
}

export function useUpdateInteractionType() {
    const queryClient = useQueryClient();

    return $api.useMutation("put", "/interactions/type", {
        onSettled() {
            queryClient.refetchQueries(
                $api.queryOptions("get", "/interactions/type")
            )
        }
    });
}

export function useDeleteParameter() {
    const queryClient = useQueryClient();

    return $api.useMutation("delete", "/parameters", {
        onSettled() {
            queryClient.refetchQueries(
                $api.queryOptions("get", "/parameters")
            )
        }
    });
}

export function useCreateParameter() {
    const queryClient = useQueryClient();

    return $api.useMutation("post", "/parameters", {
        onSettled() {
            queryClient.refetchQueries(
                $api.queryOptions("get", "/parameters")
            )
        }
    });
}

export function useUpdateParameter() {
    const queryClient = useQueryClient();

    return $api.useMutation("put", "/parameters", {
        onSettled() {
            queryClient.refetchQueries(
                $api.queryOptions("get", "/parameters")
            )
        }
    });
}