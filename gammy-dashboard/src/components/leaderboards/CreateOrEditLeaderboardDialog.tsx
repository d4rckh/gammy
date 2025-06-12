import {
  useCreateLeaderboard,
  useUpdateLeaderboard,
} from "@/lib/api/mutations";
import type { components } from "@/lib/api/v1";
import {
  Dialog,
  DialogContent,
  DialogTitle,
  DialogTrigger,
} from "../ui/dialog";
import { Button } from "../ui/button";
import { useEffect, useState } from "react";
import { Input } from "../ui/input";
import { Label } from "../ui/label";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "../ui/select";
import { useGameStats } from "@/lib/api/queries";

type LeaderboardEntity = components["schemas"]["LeaderboardEntity"];
type LeaderboardCreateRequest = components["schemas"]["LeaderboardCreateRequest"];
type LeaderboardSortMethod = components["schemas"]["LeaderboardSortMethod"];

export default function CreateOrEditLeaderboardDialog({
  leaderboard,
}: {
  leaderboard?: LeaderboardEntity;
}) {
  const create = useCreateLeaderboard();
  const update = useUpdateLeaderboard();
  
  const [open, setOpen] = useState(false);

  const {data: gameStats} = useGameStats();

  const [body, setBody] = useState<LeaderboardCreateRequest>({
    apiName: "",
    displayName: "",
    description: "",
    sortMethod: "DESCENDING",
    gameStatApiName: "",
  });

  const [errors, setErrors] = useState<{ [key: string]: boolean }>({});

  useEffect(() => {
    if (leaderboard) {
      setBody({
        apiName: leaderboard.apiName ?? "",
        displayName: leaderboard.displayName ?? "",
        description: leaderboard.description ?? "",
        sortMethod: leaderboard.sortMethod ?? "DESCENDING",
        gameStatApiName: leaderboard.gameStat?.apiName ?? "",
      });
    }
  }, [leaderboard]);

  const handleChange = (
    field: keyof LeaderboardCreateRequest,
    value: string
  ) => {
    setBody((prev) => ({ ...prev, [field]: value }));
    setErrors((prev) => ({ ...prev, [field]: false }));
  };

  const validateAndSubmit = () => {
    const newErrors: typeof errors = {};
    if (!body.apiName?.trim()) newErrors.apiName = true;
    if (!body.displayName?.trim()) newErrors.displayName = true;
    if (!body.sortMethod) newErrors.sortMethod = true;
    if (!body.gameStatApiName?.trim()) newErrors.gameStatApiName = true;

    setErrors(newErrors);
    if (Object.keys(newErrors).length > 0) return;

    const action = leaderboard
      ? update.mutateAsync({ body: {
        ...leaderboard,
        ...body,
        gameStat: gameStats?.find(a => a.apiName == body.gameStatApiName)
      } })
      : create.mutateAsync({ body });

    action.then(() => setOpen(false));
  };

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button size="sm">{leaderboard ? "Edit" : "New Leaderboard"}</Button>
      </DialogTrigger>
      <DialogContent>
        <DialogTitle>
          {leaderboard ? "Edit Leaderboard" : "New Leaderboard"}
        </DialogTitle>

        <Label>API Name</Label>
        <Input
          placeholder="API Name"
          value={body.apiName ?? ""}
          className={errors.apiName ? "border-red-500" : ""}
          onChange={(e) => handleChange("apiName", e.target.value)}
        />

        <Label>Display Name</Label>
        <Input
          placeholder="Display Name"
          value={body.displayName ?? ""}
          className={errors.displayName ? "border-red-500" : ""}
          onChange={(e) => handleChange("displayName", e.target.value)}
        />

        <Label>Description</Label>
        <Input
          placeholder="Description"
          value={body.description ?? ""}
          onChange={(e) => handleChange("description", e.target.value)}
        />

        <Label>Sort Method</Label>
        <Select
          value={body.sortMethod ?? ""}
          onValueChange={(e) => handleChange("sortMethod", e as LeaderboardSortMethod)}
        >
          <SelectTrigger className="w-full">
            <SelectValue placeholder="Select sort method" />
          </SelectTrigger>
          <SelectContent>
            <SelectItem value="ASCENDING">ASCENDING</SelectItem>
            <SelectItem value="DESCENDING">DESCENDING</SelectItem>
          </SelectContent>
        </Select>

        <Label>Game Stat</Label>
        <Select
          value={body.gameStatApiName ?? ""}
          onValueChange={(e) => handleChange("gameStatApiName", e)}
        >
          <SelectTrigger className="w-full">
            <SelectValue placeholder="Select Game Stat" />
          </SelectTrigger>
          <SelectContent>
            {
              gameStats && gameStats.map(stat =>
                <SelectItem value={stat.apiName ?? ""} key={stat.id}>{stat.displayName} - {stat.description}</SelectItem>
              )
            }
          </SelectContent>
        </Select>

        <Button onClick={validateAndSubmit}>
          {leaderboard ? "Save" : "Create"}
        </Button>
      </DialogContent>
    </Dialog>
  );
}
