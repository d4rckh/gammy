import { Button } from "@/components/ui/button";
import { Dialog, DialogClose, DialogContent, DialogTitle, DialogTrigger } from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Select, SelectContent, SelectItem, SelectLabel, SelectTrigger, SelectValue } from "@/components/ui/select";
import { useCreatePlayerParameterOverrideMutation } from "@/lib/api/mutations";
import { useParameters } from "@/lib/api/queries";
import type { components } from "@/lib/api/v1";
import { useState } from "react";

export default function CreatePlayerOverrideForm({ playerId }: { playerId: number }) {
  const createOverride = useCreatePlayerParameterOverrideMutation(playerId);

  const { data: parameters } = useParameters();

  const [overridingFor, setOverridingFor] = useState<components["schemas"]["ParameterEntity"] | null>(null);
  const [value, setValue] = useState("");

  const [open, setOpen] = useState(false);

  return <Dialog open={open} onOpenChange={setOpen}>

    <DialogTrigger asChild>
      <Button size={"sm"}>New override</Button>
    </DialogTrigger>

    <DialogContent>
      <DialogTitle>New player parameter override</DialogTitle>

      {
        parameters && <Select value={overridingFor != null ? overridingFor.path : ""} onValueChange={(path) => {
          setOverridingFor(parameters.find(param => param.path == path) ?? null)
          setValue("");
        }}>
          <SelectTrigger className="w-full">
            <SelectValue placeholder="Select a parameter to override" />
          </SelectTrigger>
          <SelectContent>
            {
              parameters.map(parameter =>
                <SelectItem key={parameter.id} value={parameter.path ?? "."}>{parameter.path}</SelectItem>
              )
            }
          </SelectContent>
        </Select>
      }

      {
        overridingFor &&
        <Input value={value} onChange={(e) => setValue(e.target.value)} placeholder={`New value (Default value: ${overridingFor.value})`}></Input>
      }

      {
        overridingFor &&
        <Button onClick={() => {
          createOverride.mutateAsync({
            body: {
              path: overridingFor?.path,
              playerId,
              value
            }
          }).then(a => setOpen(false));
        }}>Create</Button>
      }
    </DialogContent>

  </Dialog>
}